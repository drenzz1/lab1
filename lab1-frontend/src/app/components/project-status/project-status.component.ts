import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {ProjectService} from "../../services/project.service";

@Component({
  selector: 'app-project-status',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    DatePipe,
    NgForOf
  ],
  templateUrl: './project-status.component.html',
  styleUrl: './project-status.component.css'
})
export class ProjectListComponent implements OnInit {
  projects: any[] = []; // Replace with your actual project model

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.projectService.getAllProjectsByManager().subscribe(data=>{
      this.projects=data;
    })
  }
}
