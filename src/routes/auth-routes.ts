import { Router } from "express";
import AuthController from "../controllers/auth-controller";

const router = Router();

router.route("/login").post(AuthController.login);

router.route("/register").post(AuthController.register);

router.route("/verify-otp").post(AuthController.verifyOtp);

router.route("/resend-otp").post(AuthController.resendOtp);

router.route("/logout").delete(AuthController.logout);

router.route("/refresh").get(AuthController.refresh);

export { router as authRouter };
