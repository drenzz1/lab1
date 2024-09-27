import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  name : string |null = ''

  constructor(private userService :AuthService) {
  }

  ngOnInit(): void {
    this.name = this.userService.getEmailFromToken();
    }

  logOut() {
    this.userService.logout();
  }
}
