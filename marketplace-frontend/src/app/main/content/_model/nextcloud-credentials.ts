export class NextcloudCredentials {
  domain: string;
  username: string;
  password: string;

  constructor(domain: string, username: string, password: string) {
    this.domain = domain;
    this.username = username;
    this.password = password;
  }
}
