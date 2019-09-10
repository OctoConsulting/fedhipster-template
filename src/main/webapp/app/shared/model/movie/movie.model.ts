export interface IMovie {
  id?: number;
  name?: string;
  average?: number;
}

export class Movie implements IMovie {
  constructor(public id?: number, public name?: string, public average?: number) {}
}
