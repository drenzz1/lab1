export enum Status {
  OPEN = "Open",
  IN_PROGRESS = "In Progress",
  COMPLETE = "Completed",
}


export const StatusArray = Object.keys(Status).map(key => ({ value: key, display: Status[key as keyof typeof Status] }));
