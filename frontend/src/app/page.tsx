import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Accueil - Wine Ecommerce',
  description: 'Découvrez notre sélection exceptionnelle de vins français et internationaux',
};

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 to-indigo-100">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-6">
            <div className="flex items-center">
              <h1 className="text-2xl font-bold text-gray-900">🍷 Wine Ecommerce</h1>
            </div>
            <nav className="hidden md:flex space-x-8">
              <a href="#" className="text-gray-500 hover:text-gray-900">Vins</a>
              <a href="#" className="text-gray-500 hover:text-gray-900">Régions</a>
              <a href="#" className="text-gray-500 hover:text-gray-900">Promotions</a>
              <a href="#" className="text-gray-500 hover:text-gray-900">À propos</a>
            </nav>
            <div className="flex items-center space-x-4">
              <button className="text-gray-500 hover:text-gray-900">
                Connexion
              </button>
              <button className="bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700">
                Panier (0)
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center">
          <h2 className="text-4xl font-bold text-gray-900 sm:text-6xl">
            Découvrez nos
            <span className="text-purple-600"> vins d'exception</span>
          </h2>
          <p className="mt-6 text-lg leading-8 text-gray-600 max-w-2xl mx-auto">
            Une sélection rigoureuse de vins français et internationaux pour tous les palais et toutes les occasions.
          </p>
          <div className="mt-10 flex items-center justify-center gap-x-6">
            <button className="bg-purple-600 px-6 py-3 text-lg font-semibold text-white shadow-sm hover:bg-purple-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-purple-600 rounded-md">
              Découvrir nos vins
            </button>
            <button className="text-lg font-semibold leading-6 text-gray-900 hover:text-purple-600">
              En savoir plus <span aria-hidden="true">→</span>
            </button>
          </div>
        </div>

        {/* Features */}
        <div className="mt-20">
          <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <div className="text-purple-600 text-3xl mb-4">🍇</div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Sélection Premium</h3>
              <p className="text-gray-600">Des vins soigneusement sélectionnés par nos experts sommeliers.</p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <div className="text-purple-600 text-3xl mb-4">🚚</div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Livraison Rapide</h3>
              <p className="text-gray-600">Livraison gratuite à partir de 75€ d'achat, emballage sécurisé.</p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <div className="text-purple-600 text-3xl mb-4">🏆</div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Qualité Garantie</h3>
              <p className="text-gray-600">Conservation optimale et garantie de satisfaction ou remboursé.</p>
            </div>
          </div>
        </div>

        {/* Wine Categories */}
        <div className="mt-20">
          <h3 className="text-2xl font-bold text-gray-900 text-center mb-12">Nos Catégories</h3>
          <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
            {[
              { name: 'Vins Rouges', color: 'bg-red-500', count: '245 vins' },
              { name: 'Vins Blancs', color: 'bg-yellow-400', count: '189 vins' },
              { name: 'Vins Rosés', color: 'bg-pink-400', count: '78 vins' },
              { name: 'Champagnes', color: 'bg-blue-400', count: '56 vins' },
            ].map((category) => (
              <div key={category.name} className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow">
                <div className={`${category.color} h-32`}></div>
                <div className="p-4">
                  <h4 className="font-semibold text-gray-900">{category.name}</h4>
                  <p className="text-sm text-gray-600">{category.count}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-gray-900 text-white mt-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div>
              <h5 className="text-lg font-semibold mb-4">Wine Ecommerce</h5>
              <p className="text-gray-400 text-sm">
                Votre caviste en ligne pour une sélection premium de vins d'exception.
              </p>
            </div>
            <div>
              <h5 className="text-lg font-semibold mb-4">Navigation</h5>
              <ul className="space-y-2 text-sm text-gray-400">
                <li><a href="#" className="hover:text-white">Nos vins</a></li>
                <li><a href="#" className="hover:text-white">Régions</a></li>
                <li><a href="#" className="hover:text-white">Promotions</a></li>
                <li><a href="#" className="hover:text-white">Blog</a></li>
              </ul>
            </div>
            <div>
              <h5 className="text-lg font-semibold mb-4">Service Client</h5>
              <ul className="space-y-2 text-sm text-gray-400">
                <li><a href="#" className="hover:text-white">Contact</a></li>
                <li><a href="#" className="hover:text-white">Livraison</a></li>
                <li><a href="#" className="hover:text-white">Retours</a></li>
                <li><a href="#" className="hover:text-white">FAQ</a></li>
              </ul>
            </div>
            <div>
              <h5 className="text-lg font-semibold mb-4">Informations</h5>
              <ul className="space-y-2 text-sm text-gray-400">
                <li><a href="#" className="hover:text-white">Mentions légales</a></li>
                <li><a href="#" className="hover:text-white">CGV</a></li>
                <li><a href="#" className="hover:text-white">Confidentialité</a></li>
              </ul>
            </div>
          </div>
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-sm text-gray-400">
            <p>&copy; 2024 Wine Ecommerce. Tous droits réservés.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}