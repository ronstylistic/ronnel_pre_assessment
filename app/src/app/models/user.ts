export class User {
  id: string = '';
  email: string = '';
  firstName: string = '';
  middleName: string = '';
  lastName: string = '';
  
  constructor(value: any = {}) {
    Object.assign(this, value);
  }

  static parseArray(json: any[]): User[] {
    const arr = [];

    if (json) {
      for (const j of json) {
        if (j) {
          arr.push(new User(j));
        }
      }
    }

    return arr;
  }

  getName(): string {
    if (!this.firstName || !this.lastName) {
      return this.email;
    } else {
      return `${this.firstName} ${this.lastName}`;
    }
  }
}
