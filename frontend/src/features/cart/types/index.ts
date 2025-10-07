import type { User } from '../../auth/types';
import type { Product } from '../../products/types';

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
