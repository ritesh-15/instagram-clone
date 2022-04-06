import { NextFunction, Request, Response } from "express";
import CreateHttpErrors from "../errors/CreateHttpErrors";
import User from "../models/UserModel";
import bcrypt from "bcrypt";
import JWTTokens from "../utils/JWTTokens";
import Token from "../models/TokensModel";
import OtpService from "../utils/OtpService";
import EmailSender from "../utils/EmailSender";
import { UserInterface } from "../interfaces/UserInterface";

class AuthController {
  // @desc Login user
  // @access Public
  // @route POST /api/v1/auth/login
  static async login(req: Request, res: Response, next: NextFunction) {
    const { email, password } = req.body;

    if (!email || !password) {
      return next(
        CreateHttpErrors.unProcessableEntity("Email and password required")
      );
    }

    try {
      const user = await User.findOne({ email }).select("+password");

      if (!user) {
        return next(CreateHttpErrors.notFound("User not found!"));
      }

      const isAuth = await bcrypt.compare(password, user.password);

      if (!isAuth) {
        return next(CreateHttpErrors.unauthorized("Invalid credentials!"));
      }

      const { accessToken, refreshToken } = JWTTokens.generateTokens(user._id);

      await Token.create({ userId: user._id, refreshToken });

      res.cookie("accessToken", accessToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      res.cookie("refreshToken", refreshToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      return res.json({
        success: true,
        user: user,
        tokens: {
          accessToken,
          refreshToken,
        },
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route POST /api/v1/auth/logout
  // @desc Logout user
  // @access Private
  static async logout(req: Request, res: Response, next: NextFunction) {
    const currentUser = req.user as UserInterface;
    try {
      await Token.deleteMany({ userId: currentUser._id });

      res.clearCookie("accessToken");
      res.clearCookie("refreshToken");

      return res.status(202).json({
        success: true,
        message: "Log out successfull!",
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route POST /api/v1/auth/refresh
  // @desc Refresh JWT token
  // @access Private
  static async refresh(req: Request, res: Response, next: NextFunction) {
    let { refreshToken: recivedRefreshToken } = req.cookies;

    if (!recivedRefreshToken) {
      const token = req.headers["refreshtoken"]?.toString();
      recivedRefreshToken = token?.split(" ")[1];
    }

    try {
      JWTTokens.validateRefreshToken(recivedRefreshToken);

      const isValideToken = await Token.findOne({
        refreshToken: recivedRefreshToken,
      });

      if (!isValideToken)
        return next(CreateHttpErrors.unauthorized("Token not found!"));

      const user = await User.findOne({ _id: isValideToken.userId });

      if (!user) return next(CreateHttpErrors.notFound("User not found!"));

      await Token.deleteMany({ userId: user._id });

      const { accessToken, refreshToken } = JWTTokens.generateTokens(user._id);

      await Token.create({ userId: user._id, refreshToken });

      res.cookie("accessToken", accessToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      res.cookie("refreshToken", refreshToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      return res.json({
        success: true,
        user: user,
        tokens: {
          accessToken,
          refreshToken,
        },
      });
    } catch (error: any) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route POST /api/v1/auth/forgot-password
  // @desc Forgot password
  // @access Public
  static async forgotPassword(
    req: Request,
    res: Response,
    next: NextFunction
  ) {}

  // @route POST /api/v1/auth/register
  // @desc Register user
  // @access Public
  static async register(req: Request, res: Response, next: NextFunction) {
    const { email } = req.body;

    if (!email) return next(CreateHttpErrors.notFound("Email not found!"));

    try {
      let user = null;
      user = await User.findOne({ email });
      if (user) return next(CreateHttpErrors.forbidden("User already exists!"));

      user = await User.create({ email });
      const otpService = new OtpService(email);
      const hashedOtp = `${otpService.hash()}.${otpService.expiresIn}`;

      const sender = new EmailSender(
        email,
        EmailSender.EMAIL_VERIFICATION,
        "One time password",
        EmailSender.otpEmailTemplate(email, otpService.otp)
      );

      await sender.send();

      return res.json({
        hash: hashedOtp,
        email: email,
        success: true,
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route POST /api/v1/auth/verify-otp
  // @desc Verify otp
  // @access Public
  static async verifyOtp(req: Request, res: Response, next: NextFunction) {
    const { hash, email, otp } = req.body;

    if (!hash || !email || !otp)
      return next(CreateHttpErrors.notFound("Email and otp required"));

    try {
      const user = await User.findOne({ email });

      if (!user) return next(CreateHttpErrors.notFound("User not found!"));

      const [hashedOtp, time] = hash.split(".");

      const expiresIn = parseInt(time);

      if (expiresIn < Date.now())
        return next(CreateHttpErrors.forbidden("Otp expired!"));

      if (!OtpService.verify(email, otp, time, hashedOtp))
        return next(CreateHttpErrors.forbidden("Otp not valid!"));

      user.isVerified = true;

      await user.save();

      const { accessToken, refreshToken } = JWTTokens.generateTokens(user._id);

      await Token.create({ userId: user._id, refreshToken });

      res.cookie("accessToken", accessToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      res.cookie("refreshToken", refreshToken, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        httpOnly: true,
      });

      return res.json({
        success: true,
        user: user,
        tokens: {
          accessToken,
          refreshToken,
        },
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route POST /api/v1/auth/resend-otp
  // @desc Resent otp
  // @access Public
  static async resendOtp(req: Request, res: Response, next: NextFunction) {
    const { email } = req.body;

    if (!email) return next(CreateHttpErrors.notFound("Email not found!"));

    try {
      const user = await User.findOne({ email });
      if (!user) return next(CreateHttpErrors.forbidden("User not found!"));

      const otpService = new OtpService(email);
      const hashedOtp = `${otpService.hash()}.${otpService.expiresIn}`;

      const sender = new EmailSender(
        email,
        EmailSender.EMAIL_VERIFICATION,
        "One time password",
        EmailSender.otpEmailTemplate(email, otpService.otp)
      );

      await sender.send();

      return res.json({
        hash: hashedOtp,
        email: email,
        success: true,
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }
}

export default AuthController;
