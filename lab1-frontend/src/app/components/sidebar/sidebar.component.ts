import { Component } from '@angular/core';
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

  constructor(private authService : AuthService) {

  }


  hasRole(role: string): boolean {
    return true;
  }

}
