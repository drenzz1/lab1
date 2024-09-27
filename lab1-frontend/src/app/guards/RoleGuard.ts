import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import {AuthService} from "../services/auth.service";


@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const user = this.authService.getRoleFromToken(); // Assume this method returns user roles
    if (user && (user === 'Admin' || user === 'Manager')) {
      return true;
    }

    // If neither admin nor manager, redirect or return false
    this.router.parseUrl("/"); // Redirect to unauthorized page if you have one
    return false;
  }
}
