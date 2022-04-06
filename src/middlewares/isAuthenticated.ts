import passport from "passport";

export const isAuthenticated = passport.authenticate("jwt", {
  session: false,
  failureRedirect: "/auth/fail",
});
