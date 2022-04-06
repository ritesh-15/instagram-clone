import express, { Application } from "express";
import { connection } from "./database/connection";
import CreateHttpErrors from "./errors/CreateHttpErrors";
import errorHandler from "./errors/ErrorHandler";
import { authRouter } from "./routes/auth-routes";
import cookieParser from "cookie-parser";
import passport from "passport";
import { passportJwt } from "./middlewares/passportJwt";
import { userRouter } from "./routes/user-routes";
import hemlet from "helmet";
import morgan from "morgan";
import cors from "cors";
import helmet from "helmet";

const app: Application = express();

const PORT = process.env.PORT || 9000;

// database connection
connection();

// middlewares
app.use(helmet());
app.use(morgan("dev"));
app.use(express.json({ limit: "100mb" }));
app.use(express.urlencoded({ limit: "100mb", extended: false }));
app.use(cookieParser());
app.use(
  cors({
    origin: "*",
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
    credentials: true,
  })
);

// passport
passport.initialize();
passportJwt(passport);

// routes
app.use("/api/v1/auth", authRouter);
app.use("/api/v1/user", userRouter);

app.get("/auth/fail", (req, res, next) => {
  return next(CreateHttpErrors.unauthorized());
});

// error handler
app.use((req, res, next) => {
  next(CreateHttpErrors.notFound("No route match!"));
});

app.use(errorHandler);

app.listen(PORT, () => console.log(`Listening on port ${PORT} 🚀`));
