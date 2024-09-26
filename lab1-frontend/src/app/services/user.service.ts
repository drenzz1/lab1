import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from "../common/user";
import {Role} from "../common/role";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/user'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all users
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new user
  createUser(userDto: User): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/api/user/create`, userDto, {
      headers: this.getHeaders()
    });
  }

  // Method to get all roles
  // Method to update an existing user


  listAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`http://localhost:8080/api/user/role`, {
      headers: this.getHeaders()
    });
  }
  updateUser(id: number, userDto: User): Observable<void> {
    console.log(userDto)
    return this.http.put<void>(`http://localhost:8080/api/user/edit/${id}`, JSON.stringify(userDto), {
      headers: this.getHeaders()
    });
  }

  // Method to delete a user
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }

  listAllManagers(menager:string): Observable<User[]> {
    const params = new HttpParams().set('role', menager);
    return this.http.get<User[]>(`http://localhost:8080/api/user/list-by-role`,{
      headers: this.getHeaders(),
      params
    });
  }

  listAllEmployees(employee:string) {
    const params = new HttpParams().set('role', employee);
    return this.http.get<User[]>(`http://localhost:8080/api/user/list-by-role`,{
      headers: this.getHeaders(),
      params
    });
  }


  getUserById(id:number) {

    return this.http.get<User>(`${this.apiUrl}/list/${id}`,{
      headers: this.getHeaders(),

    });

  }
}
