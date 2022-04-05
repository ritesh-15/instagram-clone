import { NextFunction, Request, Response } from "express";
import CreateHttpErrors from "./CreateHttpErrors";

const errorHandler = (
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (error instanceof CreateHttpErrors) {
    return res.status(error.status).json({
      error: {
        message: error.message,
        status: error.status,
      },
      success: false,
    });
  }

  return res.status(500).json({
    error: {
      message: "Internal server error!",
      status: 500,
    },
    success: false,
  });
};

export default errorHandler;
