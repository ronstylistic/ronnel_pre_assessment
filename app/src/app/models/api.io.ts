export class RestResponse {
  success: boolean = true;
  result: any;

  error: RestError = new RestError();
}

export class RestError {
  code: number = 200;
  txt: string = "";
}
