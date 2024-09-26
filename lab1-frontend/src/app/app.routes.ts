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


export const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'layout',
    component: LayoutComponent,
    children: [
      {path: 'user-create',component:UserCreateComponent},
      {path: 'user-edit/:id',component:UserUpdateComponent},
      {path: 'task-status-update/:id',component:TaskStatusUpdateComponent},
      {path: 'project-status',component:ProjectListComponent},
      {path: 'task-create',component:TaskComponent},
      {path: 'task-edit/:id',component:TaskUpdateComponent},
      {path: 'archived-tasks',component:ArchivedTasksComponent},
      {path: 'project-create',component:ProjectCreateComponent},
      {path: 'pending-tasks',component:PendingTaskComponent},
      {path: 'project-edit/:id',component:ProjectUpdateComponent},

    ]
  },
    ]
