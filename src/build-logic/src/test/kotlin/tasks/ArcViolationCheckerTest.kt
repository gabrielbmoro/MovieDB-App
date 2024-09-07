package tasks

import org.junit.Test
import tasks.checkarcviolation.ArcViolationChecker
import tasks.checkarcviolation.CheckResult
import tasks.checkarcviolation.TargetModule
import kotlin.test.assertIs

class ArcViolationCheckerTest {

    @Test
    fun `Given a data layer with an invalid relationship when it is checked then fails`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "data",
                internalDependencies = listOf("domain", "resources"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Failure>(result)
    }

    @Test
    fun `Given a data layer with a valid relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "data",
                internalDependencies = listOf("domain"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a domain layer with no relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "domain",
                internalDependencies = emptyList(),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a platform layer with no relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "platform",
                internalDependencies = emptyList(),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a domain layer with an invalid relationship when it is checked then fails`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "domain",
                internalDependencies = listOf("data"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Failure>(result)
    }

    @Test
    fun `Given a resources layer with an invalid relationship when it is checked then fails`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "resources",
                internalDependencies = listOf("presentation"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Failure>(result)
    }

    @Test
    fun `Given a resources layer with no relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "resources",
                isFeatureModule = false,
                internalDependencies = emptyList()
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a design system layer with a valid relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "designsystem",
                internalDependencies = listOf("resources"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a feature module with an invalid relationship when it is checked then fails`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "details",
                internalDependencies = listOf("data"),
                isFeatureModule = true
            )
        )

        assertIs<CheckResult.Failure>(result)
    }

    @Test
    fun `Given a feature module with a valid relationship when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "details",
                internalDependencies = listOf("domain", "resources"),
                isFeatureModule = true
            )
        )

        assertIs<CheckResult.Success>(result)
    }

    @Test
    fun `Given a unknown module with no rule when it is checked then pass`() {
        val arcViolationChecker = ArcViolationChecker()

        val result = arcViolationChecker.check(
            TargetModule(
                moduleName = "chuck norris",
                internalDependencies = listOf("domain"),
                isFeatureModule = false
            )
        )

        assertIs<CheckResult.Success>(result)
    }
}