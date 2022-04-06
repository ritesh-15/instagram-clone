import { Request } from "express";
import { PassportStatic } from "passport";
import { Strategy as JwtStratergy } from "passport-jwt";
import Keys from "../keys/Keys";
import User from "../models/UserModel";

const extractTokenFromCookie = (req: Request) => {
  // if token not in cookies the search in headers
  let { accessToken } = req.cookies;

  if (!accessToken) {
    const token = req.headers["authorization"];
    accessToken = token?.split(" ")[1];
    const key = token?.split(" ")[0];

    if (key != "Bearer") {
      return null;
    }
  }
  return accessToken;
};

export const passportJwt = (passport: PassportStatic) => {
  passport.use(
    new JwtStratergy(
      {
        jwtFromRequest: extractTokenFromCookie,
        secretOrKey: Keys.ACCESS_TOKEN_SECRET,
      },
      async (payload, done) => {
        const _id = payload._id;

        try {
          const user = await User.findOne({ _id });

          if (!user) return done(null, false);

          done(null, user);
        } catch (error) {
          done(error, false);
        }
      }
    )
  );
};
