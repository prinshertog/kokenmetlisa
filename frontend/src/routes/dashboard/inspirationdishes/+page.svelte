<script lang="ts">
    const { data } = $props();
    const { username, role } = data;
</script>

<div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 class="text-2xl font-semibold text-gray-900">Welcome {username}</h1>
            <div class="flex items-center space-x-4">
                {#if role === 'ADMIN'}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        Users
                    </a>
                {:else}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        User Settings
                    </a>
                {/if}
                <a href="/dashboard/categories" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Categories
                </a>
                <a href="/dashboard/dishes" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Dishes
                </a>
                <form action="?/logout" method="POST" class="inline">
                    <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium cursor-pointer">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </header>
</div>