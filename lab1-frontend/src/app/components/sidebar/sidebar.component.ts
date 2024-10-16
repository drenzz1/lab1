import { Component } from '@angular/core';
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

  constructor(private authService : AuthService) {

  }


  hasRole(role: string): boolean {
    const boolean = this.authService.getRoleFromToken();
    return boolean===role;
  }

}
