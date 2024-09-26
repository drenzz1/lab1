export class LoginDto {
  private readonly username: string;
  private readonly password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }


  getEmail(): string {
    return this.username;
  }

  getPassword(): string {
    return this.password;
  }
}
