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

@Injectable()
export class Root implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
        const role = this.authService.getRoleFromToken()
        if (role === "Root User"){
          return true
        }
      return this.router.parseUrl("/");

      throw new Error("Method not implemented.");
    }
}
