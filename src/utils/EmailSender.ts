import sgmail from "@sendgrid/mail";
import Keys from "../keys/keys";

class EmailSender {
  to: string;
  subject: string;
  text: string;
  html: string;

  constructor(to: string, subject: string, text: string, html: string) {
    this.to = to;
    this.subject = subject;
    this.text = text;
    this.html = html;
  }

  send() {
    sgmail.setApiKey(Keys.SEND_GRID_API_KEY);
    const options = {
      to: this.to,
      from: {
        name: "Instagram",
        email: Keys.SEND_GRID_EMAIL,
      },
      subject: this.subject,
      text: this.text,
      html: this.html,
    };

    return sgmail.send(options);
  }

  static EMAIL_VERIFICATION = "Verification code!";

  static otpEmailTemplate(email: string, otp: number): string {
    return `
    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>

        *{
            font-family: sans-serif;
            margin: 0;
            padding: 0;
        }

        table, td{
           padding: 0;
           border: none;
        }

    </style>

</head>
<body style="background: #f6f6f6; display: flex; justify-content: center;">
    
    <table align="center" role="presentation" style="border-collapse: collapse; background: #fff; width: 100%; border-spacing: 0; border: 0; margin: 1em;">
        <tr>
            <td align="center" style="padding: 1em;">
                <img style="width: 100px; height: auto; object-fit: contain;" src="https://cdn-icons-png.flaticon.com/512/87/87390.png" alt="">
            </td>
        </tr>
        <tr>
            <td style="padding: 1em;" align="center">
                <h1 style="font-size: 1em; font-weight: 500;">Welcome to Instagram</h1>
            </td>
        </tr>
        <tr>
            <td align="center">
                <p>Your one time verification code is</p>
            </td>
        </tr>
        <tr>
            <td style="padding: 1em;" align="center">
                <div style="background: #000; width: fit-content; padding: 0.75em 1em;
                color: #fff; border-radius: 0.2em; font-size: 1rem;">${otp}</h1>
            </td>
        </tr>
    </table>

</body>
</html>
    `;
  }
}

export default EmailSender;
