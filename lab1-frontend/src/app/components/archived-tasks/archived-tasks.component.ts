import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-archived-tasks',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf
  ],
  templateUrl: './archived-tasks.component.html',
  styleUrl: './archived-tasks.component.css'
})
export class ArchivedTasksComponent implements OnInit {
  tasks: any[] = []; // Replace with your actual task model

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadArchivedTasks();
  }

  loadArchivedTasks(): void {
    this.taskService.getCompletedTasks().subscribe(data=>{
      this.tasks=data;
    })
  }
}
