import { Routes } from '@angular/router';
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LayoutComponent} from "./components/layout/layout.component";
import {HomeComponent} from "./components/home/home.component";
import {AboutComponent} from "./components/about/about.component";

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'layout',
    component: LayoutComponent,
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'about', component: AboutComponent},
      {path: '', redirectTo: 'home', pathMatch: 'full'}

    ]
  },
    ]
