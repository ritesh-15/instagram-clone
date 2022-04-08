import { Router } from "express";
import UserController from "../controllers/user-controller";
import { isAuthenticated } from "../middlewares/isAuthenticated";
import { uploader } from "../middlewares/uploadMiddleware";

const router = Router();

router.route("/activate").put(isAuthenticated, UserController.activate);

router
  .route("/change-username")
  .put(isAuthenticated, UserController.changeUsername);

router
  .route("/update-profile")
  .put(
    [isAuthenticated, uploader.single("image")],
    UserController.updateProfile
  );

export { router as userRouter };
