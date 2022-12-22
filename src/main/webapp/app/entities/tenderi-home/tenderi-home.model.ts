export interface ITenderiHome {
  id: number;
}

export type NewTenderiHome = Omit<ITenderiHome, 'id'> & { id: null };
