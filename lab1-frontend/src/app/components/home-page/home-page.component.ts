import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {LoginDto} from "../../common/login-dto";
import {NgIf} from "@angular/common";

class AuthResponse {
  public accessToken: string;
  public refreshToken: string;

  constructor(accessToken: string, refreshToken: string) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}


@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit{
  loginFormGroup: FormGroup = new FormGroup<any>({});
  loginError: boolean = false;
  ERRORMSG: string = '';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.authService.checkIsAuthenticated();
    this.loginFormGroup = this.formBuilder.group({
      login: this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: new FormControl('', [Validators.required])
      })
    });
  }

  loginButton(): void {
    this.loginFormGroup.markAllAsTouched();
    this.ERRORMSG = '';
    const email: string = this.loginFormGroup.controls['login'].get('email')?.value;
    const password: string = this.loginFormGroup.controls['login'].get('password')?.value;

    const loginDto = new LoginDto(email, password);

    if (email != '' && password != '') {
      this.authService.loginUser(loginDto).subscribe((res: AuthResponse) => {
        const authResponse: AuthResponse = res as AuthResponse;
        console.log('Access Token:', authResponse.accessToken,authResponse);
        if (res.accessToken) {
          const payload = this.authService.parseJwtPayload(res.accessToken);
          const isEnabled = true;


          if (isEnabled) {
            const role = payload.scopes;


            if (role[0] === 'Admin' || role[0] === 'Manager' || role[0] === 'Employee') {
              this.authService.setTokens(res.accessToken, res.refreshToken);
              this.router.navigateByUrl("/layout");
              // this.sharedNotificationService.readNotificationsFromWebSocket();
            } else {
              console.log("Unknown role:", role[0]);
            }
          } else {
            this.loginError = true;
            this.ERRORMSG = 'User is not enabled. Please contact support.';
          }
        } else {

          this.loginError = true;

          this.ERRORMSG = 'An unexpected error occurred. Please try again later.';
        }
      }, (error => {
        console.log("Error:", error); // Log the error here
        if (error.status === 401) {
          this.loginError = true;
          this.ERRORMSG = 'Email or password is incorrect';
        } else {
          this.loginError = true;
          this.ERRORMSG = 'Email or password is incorrect';
        }
      }));
    }
  }


}
