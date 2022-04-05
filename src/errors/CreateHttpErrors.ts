class CreateHttpErrors {
  message: string;
  status: number;
  constructor(message: string, status: number) {
    this.message = message;
    this.status = status;
  }

  toJSON() {
    return {
      message: this.message,
      status: this.status,
    };
  }

  static badRequest(message: string = "Bad request!") {
    return new CreateHttpErrors(message, 400);
  }

  static unauthorized(message: string = "Unauthorized!") {
    return new CreateHttpErrors(message, 401);
  }

  static unProcessableEntity(message: string = "Unprocessabel entity!") {
    return new CreateHttpErrors(message, 422);
  }

  static notFound(message: string = "Not found!") {
    return new CreateHttpErrors(message, 404);
  }

  static internalServerError(message: string = "Internal server error!") {
    return new CreateHttpErrors(message, 500);
  }

  static forbidden(message: string = "Forbidden!") {
    return new CreateHttpErrors(message, 403);
  }
}

export default CreateHttpErrors;
