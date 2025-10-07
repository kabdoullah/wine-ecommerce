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

// Import types from other features
import type { Category } from '../../categories/types';
import type { Review } from '../../reviews/types';
