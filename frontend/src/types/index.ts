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

export interface Product {
  id: string;
  sku: string;
  ean?: string;
  name: string;
  slug: string;
  description?: string;
  shortDescription?: string;
  producer?: string;
  region?: string;
  vintage?: number;
  alcohol?: number;
  volumeMl: number;
  format?: string;
  basePriceCents: number;
  currentPriceCents: number;
  currency: string;
  stock: number;
  productCategories: ProductCategory[];
  productPromotions: ProductPromotion[];
  reviews: Review[];
  createdAt: string;
}

export interface Category {
  id: string;
  name: string;
  slug: string;
  description?: string;
  productCategories: ProductCategory[];
  createdAt: string;
}

export interface ProductCategory {
  id: string;
  product: Product;
  category: Category;
  dateAdded: string;
  isPrimary: boolean;
  createdAt: string;
}

export interface Promotion {
  id: string;
  title: string;
  description?: string;
  discountPercentage?: number;
  startDate: string;
  endDate: string;
  productPromotions: ProductPromotion[];
  createdAt: string;
}

export interface ProductPromotion {
  id: string;
  product: Product;
  promotion: Promotion;
  startDate?: string;
  endDate?: string;
  specificDiscount?: number;
  createdAt: string;
}

export interface Cart {
  id: string;
  user: User;
  status: string;
  items: CartItem[];
  createdAt: string;
}

export interface CartItem {
  id: string;
  cart: Cart;
  product: Product;
  quantity: number;
  priceCents: number;
  createdAt: string;
}

export interface Order {
  id: string;
  user: User;
  orderNumber: string;
  status: string;
  items: OrderItem[];
  createdAt: string;
}

export interface OrderItem {
  id: string;
  order: Order;
  product: Product;
  quantity: number;
  priceCents: number;
  createdAt: string;
}

export interface Review {
  id: string;
  user: User;
  product: Product;
  rating: number;
  comment?: string;
  createdAt: string;
}

// Helper types and API interfaces
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
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

// Helper functions types
export interface PriceHelper {
  cents: number;
  euros: number;
  formatted: string;
}