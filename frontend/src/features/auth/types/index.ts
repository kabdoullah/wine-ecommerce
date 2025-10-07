export interface User {
  id: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  addresses: Address[];
  carts: Cart[];
  orders: Order[];
  reviews: Review[];
  createdAt: string;
}

export interface Address {
  id: string;
  user: User;
  street: string;
  city: string;
  zipcode: string;
  country: string;
  createdAt: string;
}

export interface AuthTokens {
  accessToken: string;
  refreshToken: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface JwtResponse {
  accessToken: string;
  tokenType: string;
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
}

// Forward declarations to avoid circular imports
export type Cart = unknown;
export type Order = unknown;
export type Review = unknown;
