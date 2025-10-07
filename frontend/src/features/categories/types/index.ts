import type { ProductCategory } from '../../products/types';

export interface Category {
  id: string;
  name: string;
  slug: string;
  description?: string;
  productCategories: ProductCategory[];
  createdAt: string;
}
