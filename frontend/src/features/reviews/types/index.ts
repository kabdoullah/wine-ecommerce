import type { User } from '../../auth/types';
import type { Product } from '../../products/types';

export interface Review {
  id: string;
  user: User;
  product: Product;
  rating: number;
  comment?: string;
  createdAt: string;
}
