export type Role = 'ADMIN' | 'LECTURER' | 'STUDENT';

export interface AuthResponse {
  accessToken: string;
  id: number;
  username: string;
  fullName: string;
  roles: Role[];
  status: 'PENDING' | 'ACTIVE' | 'REJECTED';
}
