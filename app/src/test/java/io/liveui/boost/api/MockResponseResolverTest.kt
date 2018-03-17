package io.liveui.boost.api

import android.app.Application
import io.liveui.boost.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class MockResponseResolverTest {

    var application: Application = mock(Application::class.java)

    @Test
    fun normalizeUrl() {
        var mockResponseResolver = MockResponseResolver(application)

        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"auth", "POST"), "POST/auth.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"token", "POST"), "POST/token.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams", "GET"), "GET/teams.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams", "POST"), "POST/teams.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/check", "POST"), "POST/teams/check.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/123", "GET"), "GET/teams/0.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/123", "PUT"), "PUT/teams/0.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/123/users", "GET"), "GET/teams/0/users.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/123/link", "POST"), "POST/teams/0/link.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"teams/123/unlink", "POST"), "POST/teams/0/unlink.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"overview?sort=name&filter=filter&platform=android", "GET"), "GET/overview_sort.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"apps?tags=tags", "POST"), "POST/apps_tags.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"apps/android/cz.mangoweb.boost", "GET"), "GET/apps/android/apps.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"apps/123?depth=1", "GET"), "GET/apps/0_depth.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL+"apps/123/auth", "GET"), "GET/apps/0/auth.json")
    }

}