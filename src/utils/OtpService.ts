import crypto from "crypto";
import Keys from "../keys/Keys";

class OtpService {
  data: string;
  expiresIn: number;
  payload: string;
  otp: number;

  constructor(payload: string, otp?: number, expiresIn?: number) {
    this.expiresIn = expiresIn || Date.now() + 1000 * 60 * 2;
    this.payload = payload;
    this.otp = otp || this.generateOtp();
    this.data = `${this.payload}.${this.otp}.${this.expiresIn}`;
  }

  private generateOtp(): number {
    return crypto.randomInt(100000, 999999);
  }

  hash() {
    return crypto
      .createHmac("sha256", Keys.HASH_SECRET)
      .update(this.data)
      .digest("hex");
  }

  static verify(email: string, otp: number, expiresIn: number, hash: string) {
    const generatedOtp = new OtpService(email, otp, expiresIn);
    return generatedOtp.hash() === hash;
  }
}

export default OtpService;
