import {Injectable} from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Injectable({
  providedIn: 'root',
})
export class Admin implements CanActivate {

  constructor(private authService:AuthService,private router:Router) {
  }
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {

        const role = this.authService.getRoleFromToken()

        if (role === "Admin"){
          return true
        }
      return this.router.parseUrl("/layout");


      throw new Error("Method not implemented.");
    }
}
