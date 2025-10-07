import type { User } from '../../auth/types';
import type { Product } from '../../products/types';

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
