using Bzs.Server.DataAccess;

namespace Bzs.Server.ServerService
{
    /// <summary>
    /// Represents a base server service.
    /// </summary>
    public abstract class ServerServiceBase
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ServerServiceBase" /> class.
        /// </summary>
        protected ServerServiceBase()
        {
        }

        /// <summary>
        /// Creates a container.
        /// </summary>
        /// <returns>The container.</returns>
        protected BzsEntityContainer CreateContainer()
        {
            return new BzsEntityContainer();
        }
    }
}
