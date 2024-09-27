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
export class Manager implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
      const role = this.authService.getRoleFromToken()
      if (role === "Manager"){
        return true;
      }
      return this.router.parseUrl("/layout");


      throw new Error("Method not implemented.");
    }
}
