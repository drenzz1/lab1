import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Observable, of } from 'rxjs';
import {RouterLink, RouterOutlet} from "@angular/router";
import {AsyncPipe, NgIf} from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    RouterOutlet,
    NgIf,
    RouterLink,
    AsyncPipe
  ],
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-app';
  userEmail: string | null = null;
  constructor(public authService: AuthService) {}
  ngOnInit(): void {
    this.authService.isAuthenticated.subscribe(val => {

    })
    this.authService.getUserEmail().subscribe(email => {
      this.userEmail = email;
    });
  }
  isAdmin(): Observable<boolean> {
    const userRole = this.authService.getRoleFromToken();
    return of(userRole === 'Root User');
  }
  isClub(): Observable<boolean> {
    const userRole = this.authService.getRoleFromToken();
    return of(userRole === 'Admin');
  }
  logout(): void {
    this.authService.logout();
  }
  get userId(): number | null {
    return this.authService.getUserIdFromToken();
  }
}
