import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-pending-task',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink,
    NgForOf
  ],
  templateUrl: './pending-task.component.html',
  styleUrl: './pending-task.component.css'
})
export class PendingTaskComponent  implements OnInit {
  tasks: any[] = []; // Replace with your actual task model

  constructor(private taskService: TaskService,private router:Router) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getNotCompletedTasks().subscribe(data=>{
      this.tasks=data;
    })
  }


  update(id:number) {
    this.router.navigate([`/layout/task-status-update/${id}`]); // Navigating to 'user-edit' route with user ID
  }
}
