import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {Observable} from "rxjs";

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService,private router: Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    // THIS AUTHGUARD DOES NOT LET AUTHENTICATED USERS BACK TO /LOGIN
    if(localStorage.getItem("refreshToken") != null){
      return this.router.navigateByUrl('/dashboard');
    } else {
      return true;
    }
    throw new Error('Method not implemented.');
  }

}
