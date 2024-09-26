import {User} from "./user";
import {Status} from "../enums/status";

export class ProjectDto {
  constructor(
    public id: number,
    public projectName: string,
    public projectCode: string,
    public assignedManager: User,      // Reference to UserDto
    public startDate: Date,                // Using Date type for start date
    public endDate: Date,                  // Using Date type for end date
    public projectDetail: string,
    public projectStatus: Status,          // Assuming Status is an enum
    public completeTaskCounts: number,
    public unfinishedTaskCounts: number
  ) {}
}
