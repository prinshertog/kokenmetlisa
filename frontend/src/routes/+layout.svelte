<script>
  import "../app.css";
  
  const { children } = $props();
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
        class="md:hidden p-2"
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
        <a href="/dishes/lunch" class="text-gray-700 hover:text-green-600 transition-colors">Lunch</a>
        <a href="/dishes/hoofdgerechten" class="text-gray-700 hover:text-green-600 transition-colors">Hoofdgerechten</a>
        <a href="/dishes/nagerechten" class="text-gray-700 hover:text-green-600 transition-colors">Nagerechten</a>
        <a href="/dishes/bijgerechten" class="text-gray-700 hover:text-green-600 transition-colors">Bijgerechten</a>
      </nav>
    </div>

    <!-- Mobile menu overlay -->
    {#if isMenuOpen}
      <div class="md:hidden bg-white border-t border-gray-200">
        <nav class="flex flex-col space-y-4 px-4 py-6">
          <a href="/" class="text-gray-700 hover:text-green-600 transition-colors">Home</a>
          <a href="/dishes/lunch" class="text-gray-700 hover:text-green-600 transition-colors">Lunch</a>
          <a href="/dishes/hoofdgerechten" class="text-gray-700 hover:text-green-600 transition-colors">Hoofdgerechten</a>
          <a href="/dishes/nagerechten" class="text-gray-700 hover:text-green-600 transition-colors">Nagerechten</a>
          <a href="/dishes/bijgerechten" class="text-gray-700 hover:text-green-600 transition-colors">Bijgerechten</a>
        </nav>
      </div>
    {/if}
  </header>

  <main class="container mx-auto px-4 mt-24 mb-16 flex-grow">
    {@render children()}
  </main>

  <footer class="bg-[rgb(73,105,83)] text-white py-8">
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
          <h3 class="font-bold text-lg mb-4">Contact</h3>
          <p>Email: info@kokenmetlisa.be</p>
        </div>
        <div class="text-center md:text-left">
          <h3 class="font-bold text-lg mb-4">Volg ons</h3>
          <div class="flex justify-center md:justify-start space-x-4">
            <a href="#" class="hover:text-gray-300 transition-colors">Instagram</a>
            <a href="#" class="hover:text-gray-300 transition-colors">Facebook</a>
          </div>
        </div>
      </div>
      <div class="text-center mt-8 pt-8 border-t border-white/20">
        <p>&copy; {new Date().getFullYear()} Koken met Lisa. Alle rechten voorbehouden.</p>
      </div>
    </div>
  </footer>
</div>