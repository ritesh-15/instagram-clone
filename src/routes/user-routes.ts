import { Router } from "express";
import UserController from "../controllers/user-controller";
import { isAuthenticated } from "../middlewares/isAuthenticated";

const router = Router();

router.route("/activate").put(isAuthenticated, UserController.activate);

router
  .route("/change-username")
  .put(isAuthenticated, UserController.changeUsername);

router
  .route("/update-profile")
  .put(isAuthenticated, UserController.updateProfile);

export { router as userRouter };
