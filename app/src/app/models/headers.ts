export class ApiHeaders {
  /**
   * === Options ===
   * */
  static getOptions() {
    return {
      headers: {
        'Content-Type': 'application/json'
      }
    };
  }

  static getOptionsPlain() {
    return {
      headers: {
        'Content-Type': 'text/plain'
      },
      responseType: 'blob' as 'blob'
    };
  }
}
