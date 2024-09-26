import {ProjectDto} from "./project-dto";
import {User} from "./user";
import {Status} from "../enums/status";

export class TaskDto {
  constructor(
    public id: number,
    public project: ProjectDto,           // Reference to ProjectDto
    public assignedEmployee: User,     // Reference to UserDto
    public taskSubject: string,
    public taskDetail: string,
    public taskStatus: Status,            // Assuming Status is an enum
    public assignedDate: Date              // Using Date type for assigned date
  ) {}
}
