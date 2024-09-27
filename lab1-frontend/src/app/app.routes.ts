import { Routes } from '@angular/router';
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LayoutComponent} from "./components/layout/layout.component";
import {UserCreateComponent} from "./components/user/user-create/user-create.component";
import {UserUpdateComponent} from "./components/user/user-update/user-update.component";
import {TaskComponent} from "./components/task-create/task-create.component";
import {TaskUpdateComponent} from "./components/task-update/task-update.component";
import {ProjectCreateComponent} from "./components/project-create/project-create.component";
import {ProjectUpdateComponent} from "./components/project-update/project-update.component";
import {ProjectListComponent} from "./components/project-status/project-status.component";
import {PendingTaskComponent} from "./components/pending-task/pending-task.component";
import {ArchivedTasksComponent} from "./components/archived-tasks/archived-tasks.component";
import {TaskStatusUpdateComponent} from "./components/task-status-update/task-status-update.component";
import {AuthGuard} from "./guards/auth.guard";
import {AuthGuard2} from "./guards/auth2.guard";
import {Admin} from "./guards/admin";
import {Employee} from "./guards/employee";
import {Manager} from "./guards/manager";
import {RoleGuard} from "./guards/RoleGuard";


export const routes: Routes = [
  { path: '', component: HomePageComponent ,canActivate :[AuthGuard]},
  {
    path: 'layout',
    component: LayoutComponent, canActivate : [AuthGuard2],
    children: [
      {path: 'user-create',component:UserCreateComponent , canActivate :[AuthGuard2,Admin]},
      {path: 'user-edit/:id',component:UserUpdateComponent, canActivate :[AuthGuard2,Admin]},
      {path: 'task-status-update/:id',component:TaskStatusUpdateComponent ,canActivate :[AuthGuard2,Employee]},
      {path: 'project-status',component:ProjectListComponent , canActivate :[AuthGuard2,Manager]},
      {path: 'task-create',component:TaskComponent , canActivate :[AuthGuard2,Manager]},
      {path: 'task-edit/:id',component:TaskUpdateComponent , canActivate :[AuthGuard2,Manager]},
      {path: 'archived-tasks',component:ArchivedTasksComponent , canActivate :[AuthGuard2,Employee]},
      {path: 'project-create',component:ProjectCreateComponent, canActivate :[AuthGuard2,RoleGuard]},
      {path: 'pending-tasks',component:PendingTaskComponent , canActivate :[AuthGuard2,Employee]},
      {path: 'project-edit/:id',component:ProjectUpdateComponent , canActivate :[AuthGuard2,RoleGuard]},

    ]
  },
    ]
