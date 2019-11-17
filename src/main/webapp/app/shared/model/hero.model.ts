export interface IHero {
  id?: number;
  name?: string;
  affilition?: string;
  eyeColor?: string;
  hairColor?: string;
  gender?: string;
  status?: string;
  year?: number;
  firstAppearance?: string;
  universe?: string;
  intelligence?: number;
  strength?: number;
  speed?: number;
  durability?: number;
  power?: number;
  combat?: number;
  total?: number;
}

export class Hero implements IHero {
  constructor(
    public id?: number,
    public name?: string,
    public affilition?: string,
    public eyeColor?: string,
    public hairColor?: string,
    public gender?: string,
    public status?: string,
    public year?: number,
    public firstAppearance?: string,
    public universe?: string,
    public intelligence?: number,
    public strength?: number,
    public speed?: number,
    public durability?: number,
    public power?: number,
    public combat?: number,
    public total?: number
  ) {}
}
