<script>
  import "../app.css";
  
  const { children, data } = $props();
  const { parentCategories } = data;
  let isMenuOpen = $state(false);
  
  function toggleMenu() {
    isMenuOpen = !isMenuOpen;
  }
  
</script>

<div class="flex flex-col min-h-screen">
  <header class="bg-white shadow-md fixed top-0 w-full z-50">
    <div class="container mx-auto px-4 py-2 flex items-center justify-between">
      <a href="/" class="flex items-center">
        <img 
          src="https://kokenmetlisa.be/images/kokenmetlisa.png" 
          alt="Koken met Lisa" 
          class="h-16 w-auto"
        >
      </a>
      
      <!-- Mobile menu button -->
      <button 
        class="md:hidden p-2 hover:cursor-pointer"
        onclick={toggleMenu}
        aria-label="Menu"
      >
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          {#if isMenuOpen}
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          {:else}
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          {/if}
        </svg>
      </button>

      <!-- Desktop menu -->
      <nav class="hidden md:flex space-x-8">
        <div class="relative group">
            <a rel="external" href="/" 
              class="text-gray-700 hover:text-green-600 transition-colors font-medium">
              Home
            </a>
        </div>
        {#each parentCategories as parentCategory}
          <div class="relative group">
              <a rel="external" href="/category/{parentCategory.name}" 
                class="text-gray-700 hover:text-green-600 transition-colors inline-flex items-center">
                {parentCategory.name}
                <svg class="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
              </a>
              <div class="absolute left-0 mt-2 w-48 bg-white rounded-md shadow-lg opacity-0 invisible 
                         group-hover:opacity-100 group-hover:visible transition-all duration-200 z-50">
                {#each parentCategory.childCategories as subCategory}
                  <a rel="external" href="/category/{subCategory.name}" 
                    class="block px-4 py-2 text-sm text-gray-700 hover:bg-green-50 hover:text-green-600">
                    {subCategory.name}
                  </a>
                {/each}
              </div>
            </div>
        {/each}
      </nav>

      <!-- Mobile menu overlay -->
      {#if isMenuOpen}
        <div class="md:hidden bg-white border-t border-gray-200">
          <nav class="flex flex-col px-4 py-6">
            <div class="py-2">
                <a rel="external" href="/" 
                  class="text-gray-700 hover:text-green-600 transition-colors font-medium">
                  Home
                </a>
            </div>
            {#each parentCategories as parentCategory}
              {#if parentCategory.parentCategory === null}
                <div class="py-2">
                  <a rel="external" href="/category/{parentCategory.name}" 
                    class="text-gray-700 hover:text-green-600 transition-colors font-medium">
                    {parentCategory.name}
                  </a>
                  <div class="ml-4 mt-2 space-y-2">
                    {#each parentCategory.childCategories as subCategory}
                      <a rel="external" href="/category/{subCategory.name}" 
                        class="block text-gray-600 hover:text-green-600 transition-colors text-sm">
                        {subCategory.name}
                      </a>
                    {/each}
                  </div>
                </div>
              {/if}
            {/each}
          </nav>
        </div>
      {/if}
    </div>
  </header>

  <main class="container mx-auto px-4 mt-24 mb-16 flex-grow">
    {@render children()}
  </main>

  <footer class="bg-[rgb(96,110,90)] text-white py-8">
    <div class="container mx-auto px-4">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="flex justify-center md:justify-start">
          <img 
            src="https://kokenmetlisa.be/images/kokenmetlisa.png" 
            alt="Koken met Lisa" 
            class="h-16 w-auto"
          >
        </div>
        <div class="text-center md:text-left">
          <h3 class="font-bold text-lg mb-4">Volg mij</h3>
          <div class="flex justify-center md:justify-start space-x-4">
            <a href="https://www.instagram.com/kokenmetlisa_" class="hover:text-gray-300 transition-colors">Instagram</a>
          </div>
        </div>
      </div>
      <div class="text-center mt-8 pt-8 border-t border-white/20">
        <p>&copy; {new Date().getFullYear()} Koken met Lisa. Alle rechten voorbehouden.</p>
      </div>
    </div>
  </footer>
</div>