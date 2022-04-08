import { NextFunction, Request, Response } from "express";
import CreateHttpErrors from "../errors/CreateHttpErrors";
import { UserInterface } from "../interfaces/UserInterface";
import bcrypt from "bcrypt";
import User from "../models/UserModel";
import fs from "fs";
import path from "path";
import Keys from "../keys/Keys";
import CloudinaryUpload from "../utils/CloudinaryUpload";

class UserController {
  // @route PUT /api/v1/user/activate
  // @desc Activate account
  // @access Private
  static async activate(req: Request, res: Response, next: NextFunction) {
    const { name, password } = req.body;
    const currentUser = req.user as UserInterface;

    if (!name || !password)
      return next(CreateHttpErrors.notFound("Name and password is required!"));

    try {
      const hashedPass = await bcrypt.hash(password, 10);

      const user = await User.findOneAndUpdate(
        { _id: currentUser._id },
        {
          $set: {
            name,
            password: hashedPass,
          },
        },
        { new: true }
      );

      return res.status(201).json({
        success: true,
        user,
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route PUT /api/v1/user/change-username
  // @desc Change username
  // @access Private
  static async changeUsername(req: Request, res: Response, next: NextFunction) {
    const { username } = req.body;
    const currentUser = req.user as UserInterface;

    if (!username)
      return next(CreateHttpErrors.notFound("Username is required!"));

    try {
      const isTaken = await User.findOne({ username });

      if (isTaken)
        return next(CreateHttpErrors.forbidden("Username is already taken!"));

      const user = await User.findOneAndUpdate(
        { _id: currentUser._id },
        {
          $set: {
            username,
          },
        },
        { new: true }
      );

      return res.status(201).json({
        success: true,
        user,
      });
    } catch (error) {
      next(CreateHttpErrors.internalServerError());
    }
  }

  // @route PUT /api/v1/user/update-profile
  // @desc Update profile
  // @access Private
  static async updateProfile(req: Request, res: Response, next: NextFunction) {
    const { bio, name } = req.body;
    const currentUser = req.user as UserInterface;

    try {
      if (req.file) {
        fs.unlink(
          path.join(
            __dirname,
            `../public/uploads/${currentUser.avatar.publicId}`
          ),
          (error) => {
            if (error) {
              return next(
                CreateHttpErrors.internalServerError("Something went wrong!")
              );
            }
          }
        );
      }

      const user = await User.findOneAndUpdate(
        { _id: currentUser._id },
        {
          $set: {
            bio: bio || currentUser.bio,
            name: name || currentUser.name,
            avatar: req.file
              ? {
                  url: `${Keys.APP_BASE_URL}/public/upload/${req.file.filename}`,
                  publicId: req.file.filename,
                }
              : currentUser.avatar,
          },
        },
        { $new: true }
      );

      return res.status(201).json({
        user: user,
        success: true,
      });
    } catch (error: any) {
      console.log(error.message);
      next(CreateHttpErrors.internalServerError());
    }
  }
}

export default UserController;
