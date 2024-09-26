import {Role} from "./role";
import {Gender} from "../enums/gender";


export class User {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public userName: string,
    public passWord: string,            // Optional if you want to keep it private
    public phone: string,
    public role: Role,                // Reference to RoleDto
    public gender: Gender                 // Reference to Gender enum
  ) {}
}
